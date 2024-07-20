import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Produto> produtos = new ArrayList<>();
    private static List<Pedido> pedidos = new ArrayList<>();
    private static int numeroPedidoAtual = 1;

    public static void main(String[] args) {
        carregarProdutos();
        carregarPedidos();

        int opcao;

        do {
            System.out.println("\n# Menu principal #");
            System.out.println("[1] Produtos");
            System.out.println("[2] Pedidos");
            System.out.println("[3] Sair");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    menuProdutos();
                    break;
                case 2:
                    menuPedidos();
                    break;
                case 3:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 3);

        salvarProdutos();
        salvarPedidos();
    }

    private static void menuProdutos() {
        int opcao;

        do {
            System.out.println("\n# Produtos #");
            System.out.println("[1] Incluir produto");
            System.out.println("[2] Editar quantidade estoque produto");
            System.out.println("[3] Editar preço unitário produto");
            System.out.println("[4] Excluir produto");
            System.out.println("[5] Listagem produtos");
            System.out.println("[6] Voltar ao menu principal");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    incluirProduto();
                    break;
                case 2:
                    editarQuantidadeEstoque();
                    break;
                case 3:
                    editarPrecoUnitario();
                    break;
                case 4:
                    excluirProduto();
                    break;
                case 5:
                    listarProdutos();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 6);
    }

    private static void incluirProduto() {
        int codigoProduto = produtos.isEmpty() ? 1 : produtos.get(produtos.size() - 1).getCodigoProduto() + 1;
        String nomeProduto;
        double precoUnitario;
        int quantidadeEstoque;

        System.out.println("Incluir produto:");
        System.out.print("Nome do produto: ");
        nomeProduto = scanner.next();
        System.out.print("Preço unitário: ");
        precoUnitario = lerDouble();
        System.out.print("Quantidade em estoque: ");
        quantidadeEstoque = lerInteiro();

        Produto produto = new Produto(codigoProduto, nomeProduto, precoUnitario, quantidadeEstoque);
        produtos.add(produto);

        System.out.println("Produto incluído com sucesso!");
    }

    private static void editarQuantidadeEstoque() {
        int codigoProduto;
        int quantidadeEstoque;

        System.out.println("Editar quantidade em estoque:");
        System.out.print("Código do produto: ");
        codigoProduto = lerInteiro();

        Produto produto = buscarProdutoPorCodigo(codigoProduto);

        if (produto != null) {
            System.out.print("Nova quantidade em estoque: ");
            quantidadeEstoque = lerInteiro();
            produto.setQuantidadeEstoque(quantidadeEstoque);
            System.out.println("Quantidade em estoque editada com sucesso!");
        } else {
            System.out.println("Produto não encontrado!");
        }
    }

    private static void editarPrecoUnitario() {
        int codigoProduto;
        double precoUnitario;

        System.out.println("Editar preço unitário:");
        System.out.print("Código do produto: ");
        codigoProduto = lerInteiro();

        Produto produto = buscarProdutoPorCodigo(codigoProduto);

        if (produto != null) {
            System.out.print("Novo preço unitário: ");
            precoUnitario = lerDouble();
            produto.setPrecoUnitario(precoUnitario);
            System.out.println("Preço unitário editado com sucesso!");
        } else {
            System.out.println("Produto não encontrado!");
        }
    }

    private static void excluirProduto() {
        int codigoProduto;

        System.out.println("Excluir produto:");
        System.out.print("Código do produto: ");
        codigoProduto = lerInteiro();

        Produto produto = buscarProdutoPorCodigo(codigoProduto);

        if (produto != null) {
            produtos.remove(produto);
            System.out.println("Produto excluído com sucesso!");
        } else {
            System.out.println("Produto não encontrado!");
        }
    }

    private static void listarProdutos() {
        System.out.println("Listagem de produtos:");

        System.out.printf("%-8s %-45s %-15s %-15s%n", "Código", "Nome", "Preço unitário", "Quantidade estoque");
        System.out.println("---------------------------------------------------------------------------------------");
        for (Produto produto : produtos) {
            System.out.printf("%-8d %-45s %-15.2f %-15d%n", produto.getCodigoProduto(), produto.getNomeProduto(),
                    produto.getPrecoUnitario(), produto.getQuantidadeEstoque());
        }
        System.out.println("---------------------------------------------------------------------------------------");

        double valorTotalEstoque = calcularValorTotalEstoque();
        System.out.println("Valor total estoque: " + valorTotalEstoque);
    }

    private static double calcularValorTotalEstoque() {
        double total = 0;
        for (Produto produto : produtos) {
            total += produto.getPrecoUnitario() * produto.getQuantidadeEstoque();
        }
        return total;
    }

    private static Produto buscarProdutoPorCodigo(int codigoProduto) {
        for (Produto produto : produtos) {
            if (produto.getCodigoProduto() == codigoProduto) {
                return produto;
            }
        }
        return null;
    }

    private static void menuPedidos() {
        int opcao;

        do {
            System.out.println("\n# Pedidos #");
            System.out.println("[1] Incluir pedido");
            System.out.println("[2] Listar pedidos");
            System.out.println("[3] Voltar ao menu principal");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    incluirPedido();
                    break;
                case 2:
                    listarPedidos();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 3);
    }

    private static void incluirPedido() {
        Pedido pedido = new Pedido(numeroPedidoAtual);

        int opcao = 0; // Inicializa com um valor qualquer diferente de 1 e 2

        do {
            System.out.println("\nInclusão de item no pedido #" + numeroPedidoAtual);
            System.out.print("Código do produto: ");
            int codigoProduto = lerInteiro();

            Produto produto = buscarProdutoPorCodigo(codigoProduto);
            if (produto == null) {
                System.out.println("Produto não encontrado!");
                continue;
            }

            System.out.print("Quantidade: ");
            int quantidade = lerInteiro();

            ItemPedido itemPedido = new ItemPedido(produto, quantidade);
            pedido.adicionarItem(itemPedido);

            System.out.println("Item adicionado ao pedido com sucesso!");

            System.out.println("\nDeseja adicionar mais itens ao pedido #" + numeroPedidoAtual + "?");
            System.out.println("[1] Sim");
            System.out.println("[2] Não");

            // Limpar o buffer do scanner para evitar problemas com a próxima leitura de entrada
            scanner.nextLine();

            try {
                opcao = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida! Digite apenas números.");
                opcao = 0; // Reinicializa para entrar no loop novamente
                scanner.nextLine(); // Limpa o buffer do scanner
            }
        } while (opcao == 1);

        pedidos.add(pedido);
        numeroPedidoAtual++;
    }

    private static void listarPedidos() {
        System.out.println("Listagem de pedidos:");

        for (Pedido pedido : pedidos) {
            System.out.println("\nPedido número: " + pedido.getNumeroPedido());
            System.out.printf("%-45s %-15s %-15s %-15s%n", "Produto", "Preço unitário", "Quantidade", "Subtotal");
            System.out.println("---------------------------------------------------------------------------------------");

            double valorTotalPedido = 0;
            for (ItemPedido item : pedido.getItens()) {
                double subtotal = item.getQuantidade() * item.getProduto().getPrecoUnitario();
                valorTotalPedido += subtotal;
                System.out.printf("%-45s %-15.2f %-15d %-15.2f%n", item.getProduto().getNomeProduto(),
                        item.getProduto().getPrecoUnitario(), item.getQuantidade(), subtotal);
            }

            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println("Valor total pedido: " + valorTotalPedido);
        }
    }

    private static int lerInteiro() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite um número inteiro.");
                scanner.next(); // Limpa o buffer do scanner
            }
        }
    }

    private static double lerDouble() {
        while (true) {
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite um número real.");
                scanner.next(); // Limpa o buffer do scanner
            }
        }
    }

    private static void carregarProdutos() {
        try (BufferedReader br = new BufferedReader(new FileReader("Produtos.txt"))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] valores = linha.split(";");
                int codigoProduto = Integer.parseInt(valores[0]);
                String nomeProduto = valores[1];
                // Substituir vírgula por ponto para evitar NumberFormatException
                double precoUnitario = Double.parseDouble(valores[2].replace(',', '.'));
                int quantidadeEstoque = Integer.parseInt(valores[3]);

                Produto produto = new Produto(codigoProduto, nomeProduto, precoUnitario, quantidadeEstoque);
                produtos.add(produto);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo de produtos: " + e.getMessage());
        }
    }

    private static void carregarPedidos() {
        try (BufferedReader br = new BufferedReader(new FileReader("Pedidos.txt"))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] valores = linha.split(";");
                int numeroPedido = Integer.parseInt(valores[0]);

                Pedido pedido = new Pedido(numeroPedido);

                for (int i = 1; i < valores.length; i += 2) {
                    int codigoProduto = Integer.parseInt(valores[i]);
                    int quantidade = Integer.parseInt(valores[i + 1]);

                    Produto produto = buscarProdutoPorCodigo(codigoProduto);
                    if (produto != null) {
                        ItemPedido itemPedido = new ItemPedido(produto, quantidade);
                        pedido.adicionarItem(itemPedido);
                    }
                }

                pedidos.add(pedido);

                if (numeroPedido >= numeroPedidoAtual) {
                    numeroPedidoAtual = numeroPedido + 1;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo de pedidos: " + e.getMessage());
        }
    }

    private static void salvarProdutos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Produtos.txt"))) {
            for (Produto produto : produtos) {
                bw.write(String.format("%d;%s;%.2f;%d%n", produto.getCodigoProduto(), produto.getNomeProduto(),
                        produto.getPrecoUnitario(), produto.getQuantidadeEstoque()));
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar produtos: " + e.getMessage());
        }
    }

    private static void salvarPedidos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Pedidos.txt"))) {
            for (Pedido pedido : pedidos) {
                bw.write(pedido.getNumeroPedido() + ";");

                for (ItemPedido item : pedido.getItens()) {
                    bw.write(item.getProduto().getCodigoProduto() + ";" + item.getQuantidade() + ";");
                }

                bw.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar pedidos: " + e.getMessage());
        }
    }
}

