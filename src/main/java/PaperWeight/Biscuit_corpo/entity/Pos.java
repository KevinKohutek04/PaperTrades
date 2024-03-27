package PaperWeight.Biscuit_corpo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pos")
public class Pos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
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
    public Pos(double usdt, double openPos, int open, long user, int type) {
        this.usdt = usdt;
        this.openPos = openPos;
        this.open = open;
        this.user = user;
        this.type = type;
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
}
