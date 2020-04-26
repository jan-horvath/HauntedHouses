@GenericGenerator(
        name = "pooled_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "sequence_name", value = "sequence"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1"),
        }
)
package cz.muni.fi.pa165.hauntedhouses.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;