package entities;

import middlewares.EstoqueException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Vendedor {
    private Integer idVendedor;
    private List<Vendas> vendas = new ArrayList<>();

    public Vendedor(Integer idVendedor) {

        this.idVendedor = idVendedor;
    }

    public Integer getIdVendedor() { return idVendedor; }
    public void setIdVendedor(Integer idVendedor) { this.idVendedor = idVendedor; }
    public List<Vendas> getVendas() { return vendas; }
    public void addVendas(Vendas vendas) { this.vendas.add(vendas); }



}