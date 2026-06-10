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
                        Produto produtoExistente = produtos.stream().filter(p -> p.getIdProduto().equals(id)).findFirst().orElse(null);
                        if(produtoExistente != null) {
                            throw new EstoqueException("Produto já cadastrado!");
                        }

                        System.out.println("Digite o nome do produto: ");
                        sc.nextLine();
                        String nome = sc.nextLine();
                        System.out.println("Digite o valor do produto: ");
                        double valor = sc.nextDouble();
                        System.out.println("Digite a categoria do produto: ");
                        sc.nextLine();
                        String categoria = sc.nextLine();

                        Categoria categoriaExistente = categorias.stream().filter(c -> c.getCategoriaProduto().equals(categoria)).findFirst().orElse(null);

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
                        Vendedor vendedorExistente = vendedores.stream().filter(v -> v.getIdVendedor().equals(idVendedor)).findFirst().orElse(null);
                        if(vendedorExistente != null) {
                            throw new EstoqueException("Vendedor já cadastrado!");
                        }
                        Vendedor vendedor = new Vendedor(idVendedor);
                        vendedores.add(vendedor);
                        menu();
                        opcao = sc.nextInt();
                        break;

                    case 3:
                        System.out.println("Digite o id da venda: ");
                        int idVenda = sc.nextInt();
                        Vendas vendaExistente = vendas.values().stream().flatMap(Collection::stream).
                                filter(v -> v.getIdVendas().equals(idVenda)).findFirst().orElse(null);
                        if(vendaExistente != null) {
                            throw new EstoqueException("ID da venda já cadastrada!");
                        }
                        System.out.println("Digite o id do vendedor: ");
                        idVendedor = sc.nextInt();
                        Vendedor buscaVendedor = vendedores.stream().filter(v -> v.getIdVendedor().equals(idVendedor)).findFirst().orElse(null);
                        if(buscaVendedor == null) {
                            throw new EstoqueException("Vendedor não encontrado ou não existe!");
                        }
                        System.out.println("ID do produto: ");
                        id = sc.nextInt();
                        Produto encontrado = produtos.stream().filter(p1 -> p1.getIdProduto().equals(id)).findFirst().orElse(null);
                        if(encontrado == null) {
                            throw new EstoqueException("Produto não encontrado ou não existe!");
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
                        vendedores.forEach( vend -> {
                            double total = vend.getVendas().stream().mapToDouble(Vendas::getValorVenda).sum();
                            totalPorVendedor.put(vend.getIdVendedor(), total);
                        });

                        totalPorVendedor.forEach((idV, total) -> System.out.println("Vendedor " + idV + " | " + total));
                        menu();
                        opcao = sc.nextInt();
                        break;
                    case 5:

                        Map<String, Double> totalPorCategoria = new HashMap<>();

                        vendas.forEach((prod, lista)-> {
                            String nomeCategoria = prod.getCategoriaProduto().getCategoriaProduto();
                            double totalProduto = lista
                                    .stream()
                                    .mapToDouble(Vendas::getValorVenda)
                                    .sum();

                            double totalAtual = totalPorCategoria.getOrDefault(nomeCategoria, 0.0);
                            totalPorCategoria.put(nomeCategoria, totalAtual + totalProduto);
                        });


                        categorias.forEach((cat) -> System.out.println("Categoria: " +
                                cat.getCategoriaProduto() + " | Total vendido: " + totalPorCategoria.get(cat.getCategoriaProduto())));

                        menu();
                        opcao = sc.nextInt();
                        break;
                    case 6:

                        Vendedor maiorFaturamentoVendedor = vendedores.stream().max( Comparator.comparingDouble(vend -> vend.getVendas()
                                .stream().mapToDouble(Vendas::getValorVenda).sum())).orElse(null);

                        if(maiorFaturamentoVendedor != null) {
                            double totalFaturamento = maiorFaturamentoVendedor.getVendas().stream().mapToDouble(Vendas::getValorVenda).sum();
                            System.out.println("Vendedor " + maiorFaturamentoVendedor.getIdVendedor() + " | " + totalFaturamento);
                        }else{
                            System.out.println("Nenhuma venda realizada!");
                        }

                        menu();
                        opcao = sc.nextInt();

                        break;

                    case 7:
                     categorias.forEach(cat -> System.out.println(cat.getCategoriaProduto()));
                        menu();
                        opcao = sc.nextInt();
                        break;
                    case 8:

                        List<Vendedor> ordenados = vendedores.stream()
                                .sorted(
                                        Comparator.comparingDouble((Vendedor vend) ->
                                                vend.getVendas()
                                                        .stream()
                                                        .mapToDouble(Vendas::getValorVenda)
                                                        .sum()
                                        ).reversed()
                                )
                                .toList();

                        menu();
                        opcao = sc.nextInt();
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
