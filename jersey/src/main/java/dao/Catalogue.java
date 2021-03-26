package dao;


public class Catalogue {
	
	
	private String nfcId;
	private String NomeProdotto;
	private String Categoria;
	private String Produttore;
	private String Scadenza;
	
	
	
	public Catalogue(String nfcId, String NomeProdotto, String Categoria, String Produttore, String Scadenza){
		
		setNfcId(nfcId);
		setNomeProdotto(NomeProdotto);
		setCategoria(Categoria);
		setProduttore(Produttore);
		setScadenza(Scadenza);
		
		
	}
	
	
	/**
	 * @return the nfcId
	 */
	public String getNfcId() {
		return nfcId;
	}
	/**
	 * @return the nomeProdotto
	 */
	public String getNomeProdotto() {
		return NomeProdotto;
	}
	/**
	 * @return the Categoria
	 */
	public String getCategoria(){
		return Categoria;
	}
	/**
	 * @return the produttore
	 */
	public String getProduttore() {
		return Produttore;
	}
	/**
	 * @return the scadenza
	 */
	public String getScadenza() {
		return Scadenza;
	}
	/**
	 * @param nfcId the nfcId to set
	 */
	public void setNfcId(String nfcId) {
		this.nfcId = nfcId;
	}
	/**
	 * @param nomeProdotto the nomeProdotto to set
	 */
	public void setNomeProdotto(String nomeProdotto) {
		NomeProdotto = nomeProdotto;
	}
	/**
	 * @param Categoria
	 */
	public void setCategoria(String Categoria){
		this.Categoria = Categoria;
	}
	/**
	 * @param produttore the produttore to set
	 */
	public void setProduttore(String produttore) {
		Produttore = produttore;
	}
	/**
	 * @param scadenza the scadenza to set
	 */
	public void setScadenza(String scadenza) {
		Scadenza = scadenza;
	}

}
