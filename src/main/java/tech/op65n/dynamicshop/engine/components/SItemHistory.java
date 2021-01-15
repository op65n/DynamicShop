package tech.op65n.dynamicshop.engine.components;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SItemHistory {

    private List<HistoryMark> historyMarks = new ArrayList<>();

    @Getter
    @Setter
    @NoArgsConstructor
    public static class HistoryMark {
        private int buys;
        private int sells;
        private int transID;
    }

}
