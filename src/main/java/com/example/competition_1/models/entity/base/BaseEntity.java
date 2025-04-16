package com.example.competition_1.models.entity.base;

import gaarason.database.contract.connection.GaarasonDataSource;
import gaarason.database.eloquent.Model;
import gaarason.database.query.MySqlBuilder;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Lazy;

import java.io.Serializable;
import java.util.Collection;

@Data
@Accessors(chain = true)
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** auto generator start **/



    /** auto generator end **/

    public abstract static class BaseModel<T extends BaseEntity, K> extends Model<MySqlBuilder<T, K>, T, K> {

        @Lazy
        @Resource
        protected GaarasonDataSource gaarasonDataSource;

        @Override
        public GaarasonDataSource getGaarasonDataSource(){
            return gaarasonDataSource;
        }

        /**
         * sql日志记录
         * @param sql           带占位符的sql
         * @param parameterList 参数
         */
         @Override
         public void log(String sql,Collection<?> parameterList){
            // String format = String.format(sql.replace(" ? ", "\"%s\""), parameterList.toArray());
            // log.debug("SQL complete : {}", format);
        }

    }
}