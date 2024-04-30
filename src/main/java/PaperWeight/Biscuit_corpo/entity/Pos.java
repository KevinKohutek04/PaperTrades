package PaperWeight.Biscuit_corpo.entity;

import PaperWeight.Biscuit_corpo.service.TokenDataService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "pos")
public class Pos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    boolean shortLong;//true is long/false is short/
    @NotNull//mmddyyyy
    private int open;
    @NotNull
    private double openPos;
    @NotNull
    final double usdt;
    @NotNull
    private long user;
    @NotNull
    private int type;
    @NotNull
    private  int lev = 0;
    @NotNull
    private double takeProfit;
    @NotNull
    private double stopLoss;
    public Pos(double usdt, double openPos, long user, int type,double stopLoss,double takeProfit,boolean shortLong, int lev) {
        this.usdt = usdt;
        this.openPos = openPos;
        //this.open = open;
        LocalDate currentDate = LocalDate.now();
        String fDate = currentDate.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        this.open = Integer.parseInt(fDate);
        this.user = user;
        this.type = type;
        this.takeProfit = takeProfit;
        this.stopLoss = stopLoss;
        this.shortLong = shortLong;
        this.lev = lev;
        if(shortLong) { //long   usdt 100 / lev 5 = stock can go down 20 before loss
            if(lev > 0) {
                double minStopLoss = (usdt / lev) / 100 * openPos; //2 hours todoo this<
                if (stopLoss < minStopLoss) {
                    this.stopLoss = minStopLoss;
                    //System.out.print("ADJUSTED STOPLOSS DUE TO IT BEING TO LOW OLD:" + stopLoss + " NEW: " + minStopLoss);
                }
            }
        } else { //short
            double minStopLoss = (usdt / lev / 100 * openPos) + openPos;
            if(stopLoss > minStopLoss) {
                this.stopLoss =minStopLoss;
                //System.out.print("ADJUSTED STOPLOSS DUE TO IT BEING TO LOW OLD:" + stopLoss + " NEW: " + minStopLoss);
            }
        }
    }
    public double calUsdt () {
        double cPrice = TokenDataService.getCryptoData(type);
        double cal;
        if(shortLong) {
            cal = (cPrice - openPos) / openPos * lev * usdt;
        } else {
            cal = (openPos - cPrice) / openPos * lev * usdt;
        }
        return cal;
    }
    public boolean getShortLong() {
        return shortLong;
    }

    public void setShortLong(boolean shortLong) {
        this.shortLong = shortLong;
    }

    public int getLev() {
        return lev;
    }

    public void setLev(int lev) {
        this.lev = lev;
    }

    public double getTakeProfit() {
        return takeProfit;
    }

    public void setTakeProfit(double takeProfit) {
        this.takeProfit = takeProfit;
    }

    public double getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(double stopLoss) {
        this.stopLoss = stopLoss;
    }

    public Pos () {
        usdt = 1;
    }
    public int getType() {
        return type;
    }

    public Integer getId() {
        return id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public double getOpenPos() {
        return openPos;
    }

    public void setOpenPos(double openPos) {
        this.openPos = openPos;
    }

    public double getUsdt() {
        return usdt;
    }
}
