/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.app.orcid.service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.dspace.app.orcid.OrcidQueue;
import org.dspace.content.Item;
import org.dspace.core.Context;
import org.dspace.service.DSpaceCRUDService;

public interface OrcidQueueService extends DSpaceCRUDService<OrcidQueue> {

    /**
     * Create an OrcidQueue record with the given owner and entity.
     *
     * @param context DSpace context object
     * @param owner   the owner item
     * @param entity  the entity item
     * @return the stored record
     * @throws SQLException if an SQL error occurs
     */
    public OrcidQueue create(Context context, Item owner, Item entity) throws SQLException;

    /**
     * Get the orcid queue records by the owner id.
     *
     * @param context DSpace context object
     * @param ownerId the owner item id
     * @param limit   limit
     * @param offset  offset
     * @return the orcid queue records
     * @throws SQLException if an SQL error occurs
     */
    public List<OrcidQueue> findByOwnerId(Context context, UUID ownerId, Integer limit, Integer offset)
        throws SQLException;

    /**
     * Returns the number of records on the OrcidQueue associated with the given
     * ownerId.
     *
     * @param context DSpace context object
     * @param ownerId the owner item id
     * @return the record's count
     * @throws SQLException if an SQL error occurs
     */
    long countByOwnerId(Context context, UUID ownerId) throws SQLException;

    /**
     * Delete the OrcidQueue record with the given id.
     *
     * @param context DSpace context object
     * @param id      the id of the record to be deleted
     * @throws SQLException if an SQL error occurs
     */
    public void deleteById(Context context, Integer id) throws SQLException;

    void sendToOrcid(Context context, Integer id) throws SQLException;
}
