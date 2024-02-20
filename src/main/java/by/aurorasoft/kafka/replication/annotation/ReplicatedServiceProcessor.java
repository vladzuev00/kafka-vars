package by.aurorasoft.kafka.replication.annotation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

public class ReplicatedServiceProcessor extends AbstractProcessor {

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment environment) {
        final Set<? extends Element> annotatedElements = environment.getElementsAnnotatedWith(ReplicatedService.class);
        return annotatedElements.stream().allMatch(element -> element.asType().)
    }
}
