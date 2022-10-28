import java.text.NumberFormat;
import java.util.*;

public abstract class TroupeTheatrale {
    
    public int totalAmount;
    public int VolumeCredits;

    public final NumberFormat FRMT = NumberFormat.getCurrencyInstance(Locale.US);
    public final String TRAGEDY = "tragedy";
    public final String COMEDY = "comedy";
    public final String PIECE = "Piece";
    public final String SEATS_SOLD = "Seats sold";
    public final String PRICE = "Price";
    public final String TOTAL_OWED = "Total owed";
    public final String POINTS_EARNED = "Fidelity points earned";

    public abstract void addCurrentAmountOnTotalAmount(int thisAmount);
    public abstract void addVolumeCredits(Performance perf,Play play);

    public void reInitialiseAttributs(){
        this.totalAmount = 0;
        this.VolumeCredits = 0;
    }

    public int calculValueAmountPlay(Performance perf,Play play){
        
        int thisAmount=0;

        switch (play.type) {
        case TRAGEDY:
            thisAmount = 40000;
            if (perf.audience > 30) {
            thisAmount += 1000 * (perf.audience - 30);
            }
            break;
        case COMEDY:
            thisAmount = 30000;
            if (perf.audience > 20) {
            thisAmount += 10000 + 500 * (perf.audience - 20);
            }
            thisAmount += 300 * perf.audience;
            break;
        default:
            throw new Error("unknown type: ${play.type}");
        }
        return thisAmount;
    }

    public void  updatePointFideliteGreat150(){
        if(this.VolumeCredits > 150){
          this.VolumeCredits = this.VolumeCredits - 150;
          this.totalAmount = this.totalAmount - 15;
        }
    }
}
