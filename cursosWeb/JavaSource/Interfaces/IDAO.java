package Interfaces;

import java.util.List;

public interface IDAO<T> {

	void salvar(T objeto);

	List<T> listar();

	T buscarPorId(Long id);

	T buscarPorNome(String nome);

	void alterar(T objeto);

	void excluir(Long id);
	
	T buscarPorCpf(String cpf);

}
