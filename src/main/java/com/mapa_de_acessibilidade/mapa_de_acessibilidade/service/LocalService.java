package com.mapa_de_acessibilidade.mapa_de_acessibilidade.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapa_de_acessibilidade.mapa_de_acessibilidade.client.NominatimClient;
import com.mapa_de_acessibilidade.mapa_de_acessibilidade.client.dto.NominatimResponse;
import com.mapa_de_acessibilidade.mapa_de_acessibilidade.model.Local;
import com.mapa_de_acessibilidade.mapa_de_acessibilidade.model.LocalTag;
import com.mapa_de_acessibilidade.mapa_de_acessibilidade.model.TagAcessibilidade;
import com.mapa_de_acessibilidade.mapa_de_acessibilidade.repository.LocalRepository;
import com.mapa_de_acessibilidade.mapa_de_acessibilidade.repository.TagAcessibilidadeRepository;

@Service
public class LocalService {

	private final LocalRepository localRepository;
	private final TagAcessibilidadeRepository tagRepository;
	private final NominatimClient nominatimClient;

	@Autowired
	public LocalService(LocalRepository localRepository, TagAcessibilidadeRepository tagRepository,
			NominatimClient nominatimClient) {
		this.localRepository = localRepository;
		this.tagRepository = tagRepository;
		this.nominatimClient = nominatimClient;
	}

	/**
	 * @param local
	 * @param idsTags
	 * @return
	 */
	// @Transactional garante que a operação de banco será atômica
	@Transactional
	public Local salvarLocal(Local local, Set<Long> idsTags) {

		Optional<NominatimResponse> coordenadas = nominatimClient.buscarCoordenadas(local.getEndereco());
		if (coordenadas.isPresent()) {
			local.setLatitude(coordenadas.get().getLatitude());
			local.setLongitude(coordenadas.get().getLongitude());
		} else {
			local.setLatitude(0.0);
			local.setLongitude(0.0);
			System.err.print("Aviso: Endereço não geocodificado.");
		}

		Set<TagAcessibilidade> tagsExistentes = new HashSet<>(tagRepository.findAllById(idsTags));

		// Verifica se todas as tags solicitadas foram encontradas
		if (tagsExistentes.size() != idsTags.size()) {
			System.err.println("Aviso: Algumas Tags informadas não foram encontradas no banco de dados.");
		}

		Set<LocalTag> novoRelacionamentoTags = new HashSet<>();

		// Atribui a cada LocalTag o Local e a Tag, inicializando o score em 0.0
		for (TagAcessibilidade tag : tagsExistentes) {
			LocalTag localTag = new LocalTag(local, tag);

			novoRelacionamentoTags.add(localTag);
		}

		// Configurar o Local (incluindo o novo conjunto de LocalTag)
		local.setTagsComScore(novoRelacionamentoTags);

		// O Local precisa ser referenciado de volta no LocalTag para salvar
		// corretamente
		for (LocalTag lt : novoRelacionamentoTags) {
			lt.setLocal(local);
		}

		// Salva Local e os relacionamentos de LocalTag em cascata (Devido ao
		// CascadeType.ALL)
		return localRepository.save(local);
	}

	public List<Local> buscarTodos() {
		return localRepository.findAll();
	}

	public Optional<Local> buscarPorId(Long id) {
		return localRepository.findById(id);
	}

	@Transactional
	public Local atualizarLocal(Long id, Local detalhesLocal, Set<Long> idsTags) {
		return localRepository.findById(id).map(localExistente -> {

			localExistente.setNome(detalhesLocal.getNome());
			localExistente.setDescricao(detalhesLocal.getDescricao());
			localExistente.setEndereco(detalhesLocal.getEndereco());
			localExistente.setLatitude(detalhesLocal.getLatitude());
			localExistente.setLongitude(detalhesLocal.getLongitude());

			// Lógica de tags: Reutiliza o método salvarLocal, mas é mais seguro
			// criar uma lógica separada para atualização de tags

			return salvarLocal(localExistente, idsTags);
		}).orElseThrow(() -> new RuntimeException("Local não encontrado com ID: " + id));
	}

	public void deletarLocal(Long id) {
		localRepository.deleteById(id);
	}

	/*
	 * ==================================================================== MÉTODO
	 * PARA O BE4 (Comentários) ATUALIZAR O SCORE DE CONFIANÇA
	 * ====================================================================
	 */

	/**
	 * Este método será chamado pelo Service do BE4 (Comentários) para recalcular o
	 * score de confiança de uma tag de um local após um novo feedback.
	 * 
	 * @param localId   ID do local avaliado.
	 * @param tagId     ID da tag que teve seu score alterado.
	 * @param novoScore Novo valor de confiança calculado pelo BE4.
	 */

	@Transactional
	public void atualizarConfiancaTag(Long localId, Long tagId, Double novoScore) {
		// Encontra o local
		Local local = localRepository.findById(localId)
				.orElseThrow(() -> new RuntimeException("Local não encontrado."));

		// Percorre as tagsComScore para encontrar a tag específica
		local.getTagsComScore().stream().filter(lt -> lt.getTag().getId().equals(tagId)).findFirst().ifPresent(lt -> {
			lt.setConfiancaScore(novoScore);
			// O save não é estritamente necessário se a transação for fechada (devido ao
			// @Transactional e dirty checking),
			// mas garante que a alteração será persistida.
			localRepository.save(local);

		});
	}

}