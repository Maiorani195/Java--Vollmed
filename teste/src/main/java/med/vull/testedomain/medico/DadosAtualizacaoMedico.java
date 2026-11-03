package med.vull.testedomain.medico;

import jakarta.validation.constraints.NotNull;
import med.vull.testedomain.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(


        @NotNull
        Long id,
        String telefone ,
        String nome ,
        DadosEndereco endereco) {

}
