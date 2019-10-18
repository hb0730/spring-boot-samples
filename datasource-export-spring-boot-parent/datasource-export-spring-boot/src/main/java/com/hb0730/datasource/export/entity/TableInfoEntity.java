package com.hb0730.datasource.export.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * table info entity
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class TableInfoEntity implements Serializable {
    private static final long serialVersionUID = 3626647874446959216L;

    /**
     * table name
     */
    private String table_name;
    /**
     * column name
     */
    private String column_name;
    /**
     * ordinal position
     */
    private String ordinal_position;
    /**
     * column default
     */
    private String column_default;
    /**
     * is null
     */
    private String is_nullable;
    /**
     * data type
     */
    private String data_type;
    /**
     * character maxiMun length
     */
    private String character_maximum_length;
    /**
     * character octet length
     */
    private String character_octet_length;
    /**
     * column type
     */
    private String column_type;
    /**
     * column comment
     */
    private String column_comment;
}
