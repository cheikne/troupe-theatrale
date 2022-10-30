import java.text.NumberFormat;
import java.util.*;

public interface  TroupeTheatrale {
    public final NumberFormat FRMT = NumberFormat.getCurrencyInstance(Locale.US);
    public final String TRAGEDY = "tragedy";
    public final String COMEDY = "comedy";
    public final String PIECE = "Piece";
    public final String SEATS_SOLD = "Seats sold";
    public final String PRICE = "Price";
    public final String TOTAL_OWED = "Total owed";
    public final String POINTS_EARNED = "Fidelity points earned";

    public int getTotalAmount();
    public int getVolumeCredits();
    public void addVolumeCredits(Performance perf,Play play);

    public int calculValueAmountPlay(Performance perf,Play play);
    public  void addCurrentAmountOnTotalAmount(int thisAmount);
    public void  updatePointFideliteGreat150();
    public void reInitialiseAttributs();
}
