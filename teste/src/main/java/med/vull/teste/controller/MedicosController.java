package med.vull.teste.controller;


import jakarta.validation.Valid;
import med.vull.teste.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicos")
public class MedicosController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional //ele deixa uma transação ativa com o banco de dados.
    public void  cadastrar(@RequestBody @Valid DadosCadastroMedico dados){
      repository.save(new Medico(dados));

    }

    @GetMapping
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 10 , sort ={"nome"}) Pageable paginacao){
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);

    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);




    }
    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.excluir();



    }




}
