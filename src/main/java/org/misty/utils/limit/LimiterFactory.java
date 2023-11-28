package org.misty.utils.limit;

public interface LimiterFactory extends LimiterThrown {

    default ShortLimiterBuilder shortLimiterBuilder(String term) {
        return new ShortLimiterBuilder().giveTargetTerm(term).giveThrown(this);
    }

    default IntLimiterBuilder intLimiterBuilder(String term) {
        return new IntLimiterBuilder().giveTargetTerm(term).giveThrown(this);
    }

    default LongLimiterBuilder longLimiterBuilder(String term) {
        return new LongLimiterBuilder().giveTargetTerm(term).giveThrown(this);
    }

    default FloatLimiterBuilder floatLimiterBuilder(String term) {
        return new FloatLimiterBuilder().giveTargetTerm(term).giveThrown(this);
    }

    default DoubleLimiterBuilder doubleLimiterBuilder(String term) {
        return new DoubleLimiterBuilder().giveTargetTerm(term).giveThrown(this);
    }

    default BigDecimalLimiterBuilder bigDecimalBuilder(String term) {
        return new BigDecimalLimiterBuilder().giveTargetTerm(term).giveThrown(this);
    }

    default BigIntegerLimiterBuilder bigIntegerBuilder(String term) {
        return new BigIntegerLimiterBuilder().giveTargetTerm(term).giveThrown(this);
    }

}
