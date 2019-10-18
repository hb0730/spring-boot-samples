package com.hb0730.datasource.export.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * table entity
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class TableEntity implements Serializable {
    private static final long serialVersionUID = -387125222084858843L;
    /**
     * table name
     */
    private String table_name;

    /**
     * table comment
     */
    private String table_comment;
}
