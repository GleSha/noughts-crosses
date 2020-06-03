package io.karniushin.tictactoe.core.service.handler.rule.condition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.karniushin.tictactoe.core.domain.WinResult;

public abstract class WinCondition {

    protected WinResult.WinResultType type;

    public WinResult check(final CheckParams params) {
        final int x = params.getX();
        final int y = params.getY();
        final short[][] board = params.getBoard();
        final short search = params.getSearch();
        final int threshold = params.getThreshold();
        List<WinResult.Coords> winningLine = new ArrayList<>();
        winningLine.add(new WinResult.Coords(x, y));

        NeighbourDetector before = before(x, y, board, search);
        while (before.hasNext()) {
            winningLine.add(0, before.next());
            if (winningLine.size() == threshold) {
                return new WinResult(type, search, winningLine);
            }
        }
        NeighbourDetector after = after(x, y, board, search);
        while (after.hasNext()) {
            winningLine.add(after.next());
            if (winningLine.size() == threshold) {
                return new WinResult(type, search, winningLine);
            }
        }
        return null;
    }

    protected abstract NeighbourDetector before(int x, int y, short[][] board, short search);

    protected abstract NeighbourDetector after(int x, int y, short[][] board, short search);

    protected abstract static class NeighbourDetector implements Iterator<WinResult.Coords> {

        protected final short[][] board;
        protected final short search;

        public NeighbourDetector(short[][] board, short search) {
            this.board = board;
            this.search = search;
        }
    }
}
