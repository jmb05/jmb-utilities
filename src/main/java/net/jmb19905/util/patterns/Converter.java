package net.jmb19905.util.patterns;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class Converter<T, U> {

    private final Function<T, U> fromTtoU;
    private final Function<U, T> fromUtoT;

    protected Converter(Function<T, U> fromTtoU, Function<U, T> fromUtoT) {
        this.fromTtoU = fromTtoU;
        this.fromUtoT = fromUtoT;
    }

    public final U convertFrom(final T t) {
        return fromTtoU.apply(t);
    }

    public final T convertTo(final U u) {
        return fromUtoT.apply(u);
    }

    public final List<U> convertListFrom(final Collection<T> collection) {
        return collection.stream().map(this::convertFrom).collect(Collectors.toList());
    }

    public final List<T> convertListTo(final Collection<U> collection) {
        return collection.stream().map(this::convertTo).collect(Collectors.toList());
    }

}
