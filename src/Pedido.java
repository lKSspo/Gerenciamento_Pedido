import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int numeroPedido;
    private List<ItemPedido> itens;

    public Pedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
        this.itens = new ArrayList<>();
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public double getValorTotal() {
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void printPedido() {
        System.out.printf("\nPedido número: %d%n", numeroPedido);
        System.out.println("Produto                                         Preço unitário   Quantidade   Subtotal");
        System.out.println("--------------------------------------------------------------------------------------");
        for (ItemPedido item : itens) {
            System.out.printf("%-47s %-16.2f %-12d %.2f%n", item.getProduto().getNomeProduto(), item.getProduto().getPrecoUnitario(), item.getQuantidade(), item.getSubtotal());
        }
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.printf("Valor total pedido: %.2f%n", getValorTotal());
    }
}

class ItemPedido {
    private Produto produto;
    private int quantidade;

    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getSubtotal() {
        return produto.getPrecoUnitario() * quantidade;
    }
}
