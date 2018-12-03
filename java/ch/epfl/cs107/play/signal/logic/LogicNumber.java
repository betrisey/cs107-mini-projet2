package ch.epfl.cs107.play.signal.logic;

import java.util.stream.IntStream;

public class LogicNumber extends LogicSignal {
    private float nb;
    private Logic[] e;

    public LogicNumber(float nb, Logic... e) {
        this.nb = nb;
        this.e = e;
    }

    @Override
    public boolean isOn() {
        if (e.length > 12 || nb < 0 || nb > Math.pow(2, e.length))
            return false;

        int value = IntStream.range(0, e.length - 1)
                .filter(i -> e[i].isOn())
                .map(i -> (int) Math.pow(2, i))
                .sum();

        return nb == value;
    }
}
