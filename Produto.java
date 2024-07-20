public class Produto {
    private int codigoProduto;
    private String nomeProduto;
    private double precoUnitario;
    private int quantidadeEstoque;

    public Produto(int codigoProduto, String nomeProduto, double precoUnitario, int quantidadeEstoque) {
        this.codigoProduto = codigoProduto;
        this.nomeProduto = nomeProduto;
        this.precoUnitario = precoUnitario;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public int getCodigoProduto() {
        return codigoProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String toFormattedString() {
        return String.format("%-7d %-40s %-14.2f %-19d", codigoProduto, nomeProduto, precoUnitario, quantidadeEstoque);
    }

    public double getValorTotalEstoque() {
        return precoUnitario * quantidadeEstoque;
    }
}
