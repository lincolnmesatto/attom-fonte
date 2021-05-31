package entity;

public enum TipoPerfilEnum {
	ADMIN (1, "Admin"),
	USUARIO (2, "Usuario");
	
	private final Integer value;
	private final String descricao;
	
	private TipoPerfilEnum(Integer value, String descricao) {
		this.value = value;
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public Integer getValue() {
		return value;
	}
}
