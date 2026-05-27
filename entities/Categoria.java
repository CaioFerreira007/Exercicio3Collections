package entities;

import middlewares.EstoqueException;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
    private String categoriaProduto;
    private List<Produto> produtos = new ArrayList<>();

    public Categoria(String categoriaProduto) {

        this.categoriaProduto = categoriaProduto;
    }

    public String getCategoriaProduto() { return categoriaProduto; }
    public void setCategoriaProduto(String categoriaProduto) { this.categoriaProduto = categoriaProduto; }
    public List<Produto> getProduto() { return produtos; }
    public void addProduto(Produto produto) { this.produtos.add(produto); }
    public void removeProduto(Produto produto) { this.produtos.remove(produto); }
}