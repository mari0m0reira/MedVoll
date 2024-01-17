package med.voll.api.controller;

import med.voll.api.endereco.Endereco;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicos")
public class MedicoController {

	@Autowired
	private MedicoRepository repository;
	@PostMapping
	@Transactional
	public void cadastrar(@RequestBody @Validated DadosCadastroMedico dados){
		repository.save(new Medico(dados));

	}

	@GetMapping
	public Page<DadosListagemMedico> listar(@PageableDefault(size = 10 , sort = {"nome"}) Pageable paginacao){
		return repository.findAll(paginacao).map(DadosListagemMedico::new);
	}

	/*
	@GetMapping
	public List<DadosListagemMedico> listar(){
		return repository.findAll().stream().map(DadosListagemMedico::new).toList();
	}
	*/

	@PutMapping
	@Transactional
	public void atualizar(@RequestBody @Validated DadosAtualizacaoMedico dados){
		var medico = repository.getReferenceById(dados.id());
		medico.atualizarInformacoes(dados);
	}
}
