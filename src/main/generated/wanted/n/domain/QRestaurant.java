package wanted.n.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRestaurant is a Querydsl query type for Restaurant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRestaurant extends EntityPathBase<Restaurant> {

    private static final long serialVersionUID = 1088545672L;

    public static final QRestaurant restaurant = new QRestaurant("restaurant");

    public final StringPath bizPlaceName = createString("bizPlaceName");

    public final StringPath businessSiteCircumferenceDivision = createString("businessSiteCircumferenceDivision");

    public final StringPath businessState = createString("businessState");

    public final StringPath closeDate = createString("closeDate");

    public final NumberPath<Integer> femaleEmployeeCount = createNumber("femaleEmployeeCount", Integer.class);

    public final StringPath graduatedDivision = createString("graduatedDivision");

    public final StringPath graduatedFacilityDivision = createString("graduatedFacilityDivision");

    public final StringPath id = createString("id");

    public final StringPath licenseDate = createString("licenseDate");

    public final StringPath locationPlaceArea = createString("locationPlaceArea");

    public final NumberPath<Integer> maleEmployeeCount = createNumber("maleEmployeeCount", Integer.class);

    public final StringPath multiUseBusinessEstablishment = createString("multiUseBusinessEstablishment");

    public final NumberPath<Double> rate = createNumber("rate", Double.class);

    public final NumberPath<Double> refinedLatitude = createNumber("refinedLatitude", Double.class);

    public final NumberPath<Double> refinedLongitude = createNumber("refinedLongitude", Double.class);

    public final StringPath refinedLotNumberAddress = createString("refinedLotNumberAddress");

    public final StringPath refinedRoadNameAddress = createString("refinedRoadNameAddress");

    public final StringPath refinedZipCode = createString("refinedZipCode");

    public final NumberPath<Long> reviewedCount = createNumber("reviewedCount", Long.class);

    public final ListPath<Review, QReview> reviewList = this.<Review, QReview>createList("reviewList", Review.class, QReview.class, PathInits.DIRECT2);

    public final StringPath sanitationBusinessCondition = createString("sanitationBusinessCondition");

    public final StringPath sanitationIndustryType = createString("sanitationIndustryType");

    public final StringPath sigunCode = createString("sigunCode");

    public final StringPath sigunName = createString("sigunName");

    public final NumberPath<Integer> totalEmployeeCount = createNumber("totalEmployeeCount", Integer.class);

    public final StringPath totalFacilityScale = createString("totalFacilityScale");

    public final StringPath year = createString("year");

    public QRestaurant(String variable) {
        super(Restaurant.class, forVariable(variable));
    }

    public QRestaurant(Path<? extends Restaurant> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRestaurant(PathMetadata metadata) {
        super(Restaurant.class, metadata);
    }

}

