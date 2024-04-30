package PaperWeight.Biscuit_corpo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "closedPos")
public class ClosedPos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    boolean shortLong;
    @NotNull//mmddyyyy
    private int open;
    private int close;
    @NotNull
    private double openPos;
    private double closePos;
    @NotNull
    final double usdt;
    @NotNull
    private long user;
    @NotNull
    private int type;
    @NotNull
    private  int lev;
    @NotNull
    private double stopLoss;
    @NotNull
    private double takeProfit;

    public ClosedPos(boolean shortLong, int open, double openPos, double closePos, double usdt, long user, int type, int lev, double stopLoss, double takeProfit) {
        this.shortLong = shortLong;
        this.open = open;
        //this.close = close;
        LocalDate currentDate = LocalDate.now();
        String fDate = currentDate.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        this.close = Integer.parseInt(fDate);
        this.openPos = openPos;
        this.closePos = closePos;
        this.usdt = usdt;
        this.user = user;
        this.type = type;
        this.lev = lev;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;
    }
    public ClosedPos(){this.usdt = 1;}
    public boolean isShortLong() {
        return shortLong;
    }

    public void setShortLong(boolean shortLong) {
        this.shortLong = shortLong;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public int getClose() {
        return close;
    }

    public void setClose(int close) {
        this.close = close;
    }

    public double getOpenPos() {
        return openPos;
    }

    public void setOpenPos(double openPos) {
        this.openPos = openPos;
    }

    public double getClosePos() {
        return closePos;
    }

    public void setClosePos(double closePos) {
        this.closePos = closePos;
    }

    public double getUsdt() {
        return usdt;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLev() {
        return lev;
    }

    public void setLev(int lev) {
        this.lev = lev;
    }

    public double getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(double stopLoss) {
        this.stopLoss = stopLoss;
    }

    public double getTakeProfit() {
        return takeProfit;
    }

    public void setTakeProfit(double takeProfit) {
        this.takeProfit = takeProfit;
    }
}
