package ru.lunchvoter.util;

import ru.lunchvoter.model.Vote;

public final class VoteWrapper {

    private final Vote vote;

    private final boolean isOld;

    public VoteWrapper(Vote vote, boolean isOld) {
        this.vote = vote;
        this.isOld = isOld;
    }

    public Vote getVote() {
        return vote;
    }

    public boolean isOld() {
        return isOld;
    }
}
