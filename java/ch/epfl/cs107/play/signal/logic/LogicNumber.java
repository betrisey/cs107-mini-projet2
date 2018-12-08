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

        /* FP <3
        int value = IntStream.range(0, e.length)
                .filter(i -> e[i].isOn())
                .map(i -> (int) Math.pow(2, i))
                .sum();
        */

        float value = 0;
        for (int i = 0; i < e.length; i++) {
            value += (float)(Math.pow(2, i) * e[i].getIntensity());
        }

        return value == nb;
    }
}
