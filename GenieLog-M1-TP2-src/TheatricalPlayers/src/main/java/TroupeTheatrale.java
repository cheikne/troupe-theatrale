import java.text.NumberFormat;
import java.util.*;

public abstract class TroupeTheatrale {
    
    public final NumberFormat FRMT = NumberFormat.getCurrencyInstance(Locale.US);
    public final String TRAGEDY = "tragedy";
    public final String COMEDY = "comedy";

    public final String HEADER_PAGE_HTML = "<html><head><title>Bootstrap Example</title><meta charset='utf-8'><meta name='viewport' content='width=device-width, initial-scale=1'><link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css'><script src='https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js'></script><script src='https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js'></script><script src='https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js'></script></head> ";
    public final String PIECE = "Piece";
    public final String SEATS_SOLD = "Seats sold";
    public final String PRICE = "Price";
    public final String TOTAL_OWED = "Total owed";
    public final String POINTS_EARNED = "Fidelity points earned";
    
    public final String HEADER_OF_TABLE = "<table class='table table-dark table-striped'><thead><tr><th>Piece</th><th>Seats sold</th><th>Price</th></tr></thead><tbody>";

    public abstract int calculValueAmountPlay(Performance perf,Play play);
}
