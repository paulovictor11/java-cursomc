package com.example.cursomc.domain.enums;

public enum TipoCliente {
	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private Integer cod;
	private String descricao;
	
	private TipoCliente(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public Integer getCodigo() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static TipoCliente toEnum(Integer cod) {
		if (cod == null) return null;
		
		for (TipoCliente x : TipoCliente.values()) {
			if (cod.equals(x.getCodigo())) return x;
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
