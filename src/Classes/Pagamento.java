package Classes;

import java.util.Date;

public class Pagamento {
    private double valor;
    private Date  dataPagamento;
    private boolean status = false;

    public void confirmarPagamento(){
       this.status = true;
       this.dataPagamento = new Date();
    }

}
