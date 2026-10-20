package med.vull.teste.medico;

import med.vull.teste.endereco.DadosEndereco;

public record DadosCadastroMedico(String nome , String email , String crm , Especialidade especialidade , DadosEndereco endereco) {
}
