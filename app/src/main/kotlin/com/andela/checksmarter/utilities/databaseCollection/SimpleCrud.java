package com.andela.checksmarter.utilities.databaseCollection;

import io.realm.RealmObject;
import kotlin.Unit;

/**
 * Created by CodeKenn on 29/04/16.
 */
public interface SimpleCrud<T extends RealmObject> {

    void createOrUpdate(T realmObject);

}
