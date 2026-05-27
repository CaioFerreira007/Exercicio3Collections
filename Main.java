import entities.Categoria;
import entities.Produto;
import entities.Vendas;
import entities.Vendedor;
import middlewares.EstoqueException;

import java.time.LocalDateTime;
import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Set<Produto> produtos = new HashSet<>();
            Map<Produto, List<Vendas>> vendas = new HashMap<>();
            List<Vendedor> vendedores = new ArrayList<>();
            Set<Categoria> categorias = new HashSet<>();
            menu();
            int opcao = sc.nextInt();
            while (opcao != 0) {
                switch (opcao) {
                    case 1:
                        System.out.println("Digite o id do produto: ");
                        int id = sc.nextInt();
                        boolean existe = false;
                        for(Produto prod : produtos){
                            if(prod.getIdProduto().equals(id)){
                                existe = true;
                                break;
                            }
                        }
                        if(existe){
                            throw new EstoqueException("Produto já cadastrado! ");
                        }
                        System.out.println("Digite o nome do produto: ");
                        sc.nextLine();
                        String nome = sc.nextLine();
                        System.out.println("Digite o valor do produto: ");
                        double valor = sc.nextDouble();
                        System.out.println("Digite a categoria do produto: ");
                        sc.nextLine();
                        String categoria = sc.nextLine();

                        Categoria categoriaExistente = null;

                        for(Categoria cat : categorias){
                            if(cat.getCategoriaProduto().equalsIgnoreCase(categoria)){
                                categoriaExistente = cat;
                                break;
                            }
                        }

                        if(categoriaExistente == null){
                            categoriaExistente = new Categoria(categoria);
                            categorias.add(categoriaExistente);
                        }

                        Produto p = new Produto(id, nome, categoriaExistente, valor);

                        categoriaExistente.addProduto(p);

                        produtos.add(p);
                        menu();
                        opcao = sc.nextInt();
                        break;

                    case 2:
                        System.out.println("Digite o id do vendedor: ");
                        int idVendedor = sc.nextInt();
                        Vendedor vendedor = new Vendedor(idVendedor);
                        vendedores.add(vendedor);
                        menu();
                        opcao = sc.nextInt();
                        break;

                    case 3:
                        System.out.println("Digite o id da venda: ");
                        int idVenda = sc.nextInt();
                        System.out.println("Digite o id do vendedor: ");
                        idVendedor = sc.nextInt();
                        Vendedor buscaVendedor = null;
                        for (Vendedor ved : vendedores) {
                            if (ved.getIdVendedor().equals(idVendedor)) {
                                buscaVendedor = ved;
                            }
                        }
                        if (buscaVendedor == null) {
                            System.out.println("Vendedor não encontrado!");
                            break;
                        }
                        System.out.println("Qual produto deseja vender? ");
                        id = sc.nextInt();
                        Produto encontrado = null;
                        for (Produto prod : produtos) {
                            if (prod.getIdProduto().equals(id)) {
                                encontrado = prod;
                            }
                        }
                        if (encontrado == null) {
                            System.out.println("Produto não encontrado!");
                            break;
                        }
                        System.out.println("Quantas unidades deseja vender? ");
                        int unidadesVendas = sc.nextInt();
                        LocalDateTime dataVenda = LocalDateTime.now();
                        Vendas v = new Vendas(idVenda, encontrado, buscaVendedor, unidadesVendas, dataVenda);
                        List<Vendas> listaVendas = vendas.getOrDefault(encontrado, new ArrayList<>());
                        listaVendas.add(v);
                        vendas.put(encontrado, listaVendas);
                        buscaVendedor.addVendas(v);
                        menu();
                        opcao = sc.nextInt();
                        break;

                    case 4:
                        Map<Integer, Double> totalPorVendedor = new HashMap<>();
                        for(Vendedor vend : vendedores) {
                            double total = 0.0;
                            for(Vendas venda : vend.getVendas()) {
                                total += venda.getValorVenda();
                            }
                            totalPorVendedor.put(vend.getIdVendedor(), total);
                        }
                        for(Integer idV : totalPorVendedor.keySet()) {
                            System.out.println("Vendedor: " + idV + " | Total: " + totalPorVendedor.get(idV));
                        }
                        break;
                    case 5:

                        Map<String, Double> totalPorCategoria = new HashMap<>();

                        for (Produto prod : vendas.keySet()) {

                            String nomeCategoria =
                                    prod.getCategoriaProduto().getCategoriaProduto();

                            listaVendas = vendas.get(prod);

                            double totalProduto = listaVendas
                                    .stream()
                                    .mapToDouble(Vendas::getValorVenda)
                                    .sum();

                            double totalAtual =
                                    totalPorCategoria.getOrDefault(nomeCategoria, 0.0);

                            totalPorCategoria.put(
                                    nomeCategoria,
                                    totalAtual + totalProduto
                            );
                        }

                        for (String cat : totalPorCategoria.keySet()) {

                            System.out.println(
                                    "Categoria: " + cat +
                                            " | Total vendido: " +
                                            totalPorCategoria.get(cat)
                            );
                        }

                        menu();
                        opcao = sc.nextInt();
                        break;
                    case 6:
                        Vendedor maior = null;
                        double maiorFat = 0.0;
                        for(Vendedor vend : vendedores) {
                            double total = 0.0;
                            for(Vendas venda : vend.getVendas()) {
                                total += venda.getValorVenda();
                            }
                            if(total > maiorFat) {
                                maiorFat = total;
                                maior = vend;
                            }
                        }
                        if(maior != null) {
                            System.out.println("Maior faturamento: Vendedor " + maior.getIdVendedor() + " | " + maiorFat);
                        }
                        break;

                    case 7:
                        for(Categoria cat : categorias){
                            System.out.println(cat.getCategoriaProduto());
                        }
                        menu();
                        opcao = sc.nextInt();
                        break;
                    case 8:
                        List<Vendedor> ordenados = new ArrayList<>(vendedores);
                        for(int i = 0; i < ordenados.size() - 1; i++) {
                            for(int j = i + 1; j < ordenados.size(); j++) {
                                double fatI = 0.0, fatJ = 0.0;
                                for(Vendas venda : ordenados.get(i).getVendas()) fatI += venda.getValorVenda();
                                for(Vendas venda : ordenados.get(j).getVendas()) fatJ += venda.getValorVenda();
                                if(fatJ > fatI) {
                                    Vendedor temp = ordenados.get(i);
                                    ordenados.set(i, ordenados.get(j));
                                    ordenados.set(j, temp);
                                }
                            }
                        }
                        for(int i = 0; i < ordenados.size(); i++) {
                            double fat = 0.0;
                            for(Vendas venda : ordenados.get(i).getVendas()) fat += venda.getValorVenda();
                            System.out.println((i+1) + "º - Vendedor " + ordenados.get(i).getIdVendedor() + " | " + fat);
                        }
                        break;
                    default:
                        System.out.println("Opção inválida!");
                        menu();
                        opcao = sc.nextInt();
                        break;
                }
            }
        }catch (EstoqueException e){
            System.out.println(e.getMessage());
        }finally {
            sc.close();

        }
    }

    static void menu() {
        System.out.println("1 - Cadastrar Produto");
        System.out.println("2 - Cadastrar Vendedor");
        System.out.println("3 - Registrar Venda");
        System.out.println("4 - Relatório de vendas por vendedor");
        System.out.println("5 - Relatório de total vendido por categoria");
        System.out.println("6 - Vendedor com maior faturamento");
        System.out.println("7 - Listar categorias");
        System.out.println("8 - Ranking do maior para o menor vendedor");
        System.out.println("0 - Sair");
    }
}