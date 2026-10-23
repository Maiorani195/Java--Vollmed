package med.vull.teste.medico;

import jakarta.validation.constraints.NotNull;
import med.vull.teste.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(


        @NotNull
        Long id,
        String telefone ,
        String nome ,
        DadosEndereco endereco) {

}
