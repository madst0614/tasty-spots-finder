package wanted.n.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSggLatLon is a Querydsl query type for SggLatLon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSggLatLon extends EntityPathBase<SggLatLon> {

    private static final long serialVersionUID = 1971622388L;

    public static final QSggLatLon sggLatLon = new QSggLatLon("sggLatLon");

    public final StringPath doSi = createString("doSi");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> lat = createNumber("lat", Double.class);

    public final NumberPath<Double> lon = createNumber("lon", Double.class);

    public final StringPath sgg = createString("sgg");

    public QSggLatLon(String variable) {
        super(SggLatLon.class, forVariable(variable));
    }

    public QSggLatLon(Path<? extends SggLatLon> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSggLatLon(PathMetadata metadata) {
        super(SggLatLon.class, metadata);
    }

}

