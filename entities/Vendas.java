package entities;

import middlewares.EstoqueException;

import java.time.LocalDateTime;

public class Vendas {
    private Integer idVendas;
    private Produto produto;
    private Vendedor vendedor;
    private Integer quantidadeVendas;
    private Double valorVenda;
    private LocalDateTime dataVenda;

    public Vendas(Integer idVendas, Produto produto, Vendedor vendedor,
                  Integer quantidadeVendas, LocalDateTime dataVenda) {

        this.idVendas = idVendas;
        this.produto = produto;
        this.vendedor = vendedor;
        this.quantidadeVendas = quantidadeVendas;
        this.dataVenda = dataVenda;
        this.valorVenda = produto.getPrecoProduto() * quantidadeVendas;
    }

    public Integer getIdVendas() { return idVendas; }
    public Produto getProduto() { return produto; }
    public Vendedor getVendedor() { return vendedor; }
    public Integer getQuantidadeVendas() { return quantidadeVendas; }
    public Double getValorVenda() { return valorVenda; }
    public LocalDateTime getDataVenda() { return dataVenda; }
}