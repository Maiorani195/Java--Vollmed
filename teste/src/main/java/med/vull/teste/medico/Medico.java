package med.vull.teste.medico;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import med.vull.teste.endereco.Endereco;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String crm;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    @Embedded
    private Endereco endereco;
    private String telefone;
    private boolean ativo;

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCrm() {
        return crm;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public Long getId() {
        return id;
    }

    public Medico(DadosCadastroMedico dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
        this.telefone = dados.telefone();
        this.ativo = true;




    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoMedico dados) {

        if(dados.nome() != null)
        this.nome = dados.nome();


        if(dados.telefone() != null)
        this.telefone = dados.telefone();

        if(dados.endereco() != null)
        this.endereco.atualizarInformacoes(dados.endereco());
    }

    public void excluir() {
        this.ativo = false;
    }
}
