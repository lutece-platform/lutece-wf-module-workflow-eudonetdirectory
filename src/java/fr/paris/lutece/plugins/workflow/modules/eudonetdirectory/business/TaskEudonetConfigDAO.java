/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.workflow.modules.eudonetdirectory.business;

import fr.paris.lutece.plugins.workflow.modules.eudonetdirectory.service.EudonetdirectoryPlugin;
import fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfigDAO;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.Collection;


/**
 *
 * TaskCreatePDFConfigDAO
 *
 */
public class TaskEudonetConfigDAO implements ITaskConfigDAO<TaskEudonetConfig>
{
    private static final String SQL_QUERY_SELECT = "SELECT id_task, id_directory, session_key_family, subscriber_login, subscriber_password, user_login, user_password, base_name, wsdl_url FROM task_create_eudonetdirectory_cf WHERE id_task = ? ;";
    private static final String SQL_QUERY_INSERT = "INSERT INTO task_create_eudonetdirectory_cf (id_task, id_directory, session_key_family, subscriber_login, subscriber_password, user_login, user_password, base_name, wsdl_url ) VALUES ( ? , ? , ? , ?, ?, ?, ?, ?, ? );";
    private static final String SQL_QUERY_DELETE = "DELETE FROM task_create_eudonetdirectory_cf WHERE id_task = ? ;";
    private static final String SQL_QUERY_UPDATE = "UPDATE task_create_eudonetdirectory_cf SET id_directory=?, session_key_family=?, subscriber_login=?, subscriber_password=?, user_login=?, user_password=?, base_name=?, wsdl_url=? WHERE id_task = ? ;";
    private static final String SQL_QUERY_NEW_PK_PARAM = "SELECT max( id_attribut ) FROM task_create_eudonet_data_cf";
    private static final String SQL_QUERY_EUDONET_SELECT = "SELECT id_attribut, id_task, order_entry, eudonet_key FROM task_create_eudonet_data_cf WHERE id_task = ? ;";
    private static final String SQL_QUERY_EUDONET_INSERT = "INSERT INTO task_create_eudonet_data_cf (id_attribut, id_task, order_entry, eudonet_key ) VALUES ( ? , ? , ? , ?);";
    private static final String SQL_QUERY_EUDONET_DELETE = "DELETE FROM task_create_eudonet_data_cf WHERE id_attribut = ? ;";
    private static final String SQL_QUERY_EUDONET_UPDATE = "UPDATE task_create_eudonet_data_cf SET id_task=?, order_entry=?, eudonet_key=?  WHERE id_attribut = ? ;";

    /**
     * Generates a new primary key
     * @param plugin The Plugin
     * @return The new primary key
     */
    public int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK_PARAM, plugin );
        daoUtil.executeQuery(  );

        int nKey = 1;

        if ( daoUtil.next(  ) )
        {
            nKey = daoUtil.getInt( 1 ) + 1;
        }

        daoUtil.free(  );

        return nKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert( TaskEudonetConfig taskEudonetConfig )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, EudonetdirectoryPlugin.getPlugin(  ) );
        daoUtil.setInt( 1, taskEudonetConfig.getIdTask(  ) );
        daoUtil.setInt( 2, taskEudonetConfig.getIdDirectory(  ) );
        daoUtil.setString( 3, taskEudonetConfig.getSessionKeyFamily(  ) );
        daoUtil.setString( 4, taskEudonetConfig.getSubscriberLogin(  ) );
        daoUtil.setString( 5, taskEudonetConfig.getSubscriberPassword(  ) );
        daoUtil.setString( 6, taskEudonetConfig.getUserLogin(  ) );
        daoUtil.setString( 7, taskEudonetConfig.getUserPassword(  ) );
        daoUtil.setString( 8, taskEudonetConfig.getBaseName(  ) );
        daoUtil.setString( 9, taskEudonetConfig.getWsUrl(  ) );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( int nIdTask )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, EudonetdirectoryPlugin.getPlugin(  ) );
        daoUtil.setInt( 1, nIdTask );
        daoUtil.executeQuery(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskEudonetConfig load( int nIdTask )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, EudonetdirectoryPlugin.getPlugin(  ) );
        daoUtil.setInt( 1, nIdTask );
        daoUtil.executeQuery(  );

        if ( daoUtil.next(  ) )
        {
            TaskEudonetConfig taskEudonetConfig = new TaskEudonetConfig(  );
            taskEudonetConfig.setIdTask( daoUtil.getInt( 1 ) );
            taskEudonetConfig.setIdDirectory( daoUtil.getInt( 2 ) );
            taskEudonetConfig.setSessionKeyFamily( daoUtil.getString( 3 ) );
            taskEudonetConfig.setSubscriberLogin( daoUtil.getString( 4 ) );
            taskEudonetConfig.setSubscriberPassword( daoUtil.getString( 5 ) );
            taskEudonetConfig.setUserLogin( daoUtil.getString( 6 ) );
            taskEudonetConfig.setUserPassword( daoUtil.getString( 7 ) );
            taskEudonetConfig.setBaseName( daoUtil.getString( 8 ) );
            taskEudonetConfig.setWsUrl( daoUtil.getString( 9 ) );

            daoUtil.free(  );

            Collection<EudonetData> listEudonetData = selectEntryList( nIdTask );
            taskEudonetConfig.setEntry( listEudonetData );

            return taskEudonetConfig;
        }
        else
        {
            daoUtil.free(  );

            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store( TaskEudonetConfig taskEudonetConfig )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, EudonetdirectoryPlugin.getPlugin(  ) );
        daoUtil.setInt( 1, taskEudonetConfig.getIdDirectory(  ) );
        daoUtil.setString( 2, taskEudonetConfig.getSessionKeyFamily(  ) );
        daoUtil.setString( 3, taskEudonetConfig.getSubscriberLogin(  ) );
        daoUtil.setString( 4, taskEudonetConfig.getSubscriberPassword(  ) );
        daoUtil.setString( 5, taskEudonetConfig.getUserLogin(  ) );
        daoUtil.setString( 6, taskEudonetConfig.getUserPassword(  ) );
        daoUtil.setString( 7, taskEudonetConfig.getBaseName(  ) );
        daoUtil.setString( 8, taskEudonetConfig.getWsUrl(  ) );
        daoUtil.setInt( 9, taskEudonetConfig.getIdTask(  ) );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
         * Creation mapping entry
         * @param eudonetData
         */
    public void insertEntry( EudonetData eudonetData )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_EUDONET_INSERT, EudonetdirectoryPlugin.getPlugin(  ) );

        eudonetData.setId( newPrimaryKey( EudonetdirectoryPlugin.getPlugin(  ) ) );

        daoUtil.setInt( 1, eudonetData.getId(  ) );
        daoUtil.setInt( 2, eudonetData.getIdConfig(  ) );
        daoUtil.setInt( 3, eudonetData.getOrderEntry(  ) );
        daoUtil.setString( 4, eudonetData.getEudonetAttribut(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
         * Remove mapping entry
         * @param nKey
         */
    public void deleteEntry( int nKey )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_EUDONET_DELETE, EudonetdirectoryPlugin.getPlugin(  ) );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Update mapping entry
     * @param eudonetData
     */
    public void storeEntry( EudonetData eudonetData )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_EUDONET_UPDATE, EudonetdirectoryPlugin.getPlugin(  ) );

        daoUtil.setInt( 1, eudonetData.getId(  ) );
        daoUtil.setInt( 2, eudonetData.getIdConfig(  ) );
        daoUtil.setInt( 3, eudonetData.getOrderEntry(  ) );
        daoUtil.setString( 4, eudonetData.getEudonetAttribut(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
         * load mapping entry list
         * @param idTask
         * @return  EudonetData list
         */
    public Collection<EudonetData> selectEntryList( int nidTask )
    {
        Collection<EudonetData> parameterList = new ArrayList<EudonetData>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_EUDONET_SELECT, EudonetdirectoryPlugin.getPlugin(  ) );
        daoUtil.setInt( 1, nidTask );

        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            EudonetData parameter = new EudonetData(  );

            parameter.setId( daoUtil.getInt( 1 ) );
            parameter.setIdConfig( daoUtil.getInt( 2 ) );

            parameter.setOrderEntry( daoUtil.getInt( 3 ) );
            parameter.setEudonetAttribut( daoUtil.getString( 4 ) );

            parameterList.add( parameter );
        }

        daoUtil.free(  );

        return parameterList;
    }
}
