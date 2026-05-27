package entities;

import middlewares.EstoqueException;

public class Produto {
    private Integer idProduto;
    private String nomeProduto;
    private Categoria categoriaProduto;
    private double precoProduto;

    public Produto(Integer idProduto, String nomeProduto, Categoria categoriaProduto, double precoProduto) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.categoriaProduto = categoriaProduto;
        this.precoProduto = precoProduto;
    }

    public Integer getIdProduto() { return idProduto; }
    public void setIdProduto(Integer idProduto) { this.idProduto = idProduto; }
    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
    public Categoria getCategoriaProduto() { return categoriaProduto; }
    public void setCategoriaProduto(Categoria categoriaProduto) { this.categoriaProduto = categoriaProduto; }
    public double getPrecoProduto() { return precoProduto; }
    public void setPrecoProduto(double precoProduto) { this.precoProduto = precoProduto; }
}