package com.example.nagakrishna.farmville_new;

/**
 * Created by Naga Krishna on 11-03-2016.
 */
public class QueryBuilder {

    /**
     * Specify your database name here
     * @return
     */
    public String getDatabaseName() {
        return "farmville";
    }

    /**
     * Specify your MongoLab API here
     * @return
     */
    public String getApiKey() {
        return "FyQstvzQY5JPPyMXWs6C7kbUUrILDM-x";
    }

    /**
     * This constructs the URL that allows you to manage your database,
     * collections and documents
     * @return
     */
    public String getBaseUrl()
    {
        return "https://api.mongolab.com/api/1/databases/"+getDatabaseName()+"/collections/";
    }

    /**
     * Completes the formating of your URL and adds your API key at the end
     * @return
     */
    public String docApiKeyUrl()
    {
        return "?apiKey="+getApiKey();
    }

    /**
     * Returns the docs101 collection
     * @return
     */

    public String docApiKeyUrl(String docid)
    {
        return "/"+docid+"?apiKey="+getApiKey();
    }

    public String documentRequest()
    {
        return "temp";
    }

    /**
     * Builds a complete URL using the methods specified above
     * @return
     */
    public String buildContactsSaveURL()
    {
        return getBaseUrl()+documentRequest()+docApiKeyUrl();
    }

    public String buildContactsGetURL()
    {
        return getBaseUrl()+documentRequest()+docApiKeyUrl();
    }

    /**
     * Get a Mongodb document that corresponds to the given object id
     * @param doc_id
     * @return
     */
    public String buildContactsUpdateURL(String doc_id)
    {
        return getBaseUrl()+documentRequest()+docApiKeyUrl(doc_id);
    }

    /**
     * Formats the contact details for MongoHQ Posting
     * @param contact: Details of the person
     * @return
     */
    public String createContact(SellerDetails contact)
    {
//        return String
//                .format("{\"document\"  : {\"Product\": \"%s\", "
//                                + "\"Quantity\": \"%s\", \"Description\": \"%s\"}, \"safe\" : true}",
//                        contact.product, contact.quantity, contact.description);


        return String
                .format("{\"Product\": \"%s\", "
                                + "\"Quantity\": \"%s\", \"Description\": \"%s\", \"Image\": \"%s\"}",
                        contact.product, contact.quantity, contact.description, contact.imageEncoderValue);
    }
}
