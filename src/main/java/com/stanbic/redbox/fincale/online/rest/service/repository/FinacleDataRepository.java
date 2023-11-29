package com.stanbic.redbox.fincale.online.rest.service.repository;

import com.stanbic.redbox.fincale.online.rest.service.entity.FinacleDtdHtdTxnDataResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinacleDataRepository extends CrudRepository<FinacleDtdHtdTxnDataResponse, String> {

    @Query(value = "select a.foracid, a.cif_id cif, c.CORPORATE_NAME client_name,\n" +
            "        c.SEGMENT  market_segment_code, (\n CASE                                          \n" +
            "                                    WHEN c.SEGMENT = '001' THEN\n 'PBB' ELSE \n" +
            "                                       'CIB'\n" +
            "                                      END\n" +
            "                                ) AS market_segment,\n" +
            "                                b.ADDRESS_LINE1 address_line1,\n" +
            "                                b.ADDRESS_LINE2 address_line2,\n" +
            "                                b.ADDRESS_LINE3 address_line3,\n" +
            "                                c.gcifid  global_cif,\n" +
            "                                b.city city, \n" +
            "                                b.zip post_code ,\n" +
            "                                b.COUNTRY COUNTRY_CODE,\n" +
            "                                b.STATE province\n" +
            "from tbaadm.gam@red_link a , crmuser.address@red_link b , crmuser.corporate@red_link c where a.cif_id =:cifId and a.cif_id = b.orgkey  and a.cif_id = c.corp_key and b.PREFERREDADDRESS = 'Y'", nativeQuery = true)
    List<FinacleDtdHtdTxnDataResponse> getFinacleDtdHtdTxnDataResponse(@Param("cifId") String cifId);

    /** same as as above*/
//    @Query(value = "SELECT a.foracid, a.cif_id cif, c.CORPORATE_NAME client_name, c.SEGMENT market_segment_code, "
//            + "(CASE WHEN c.SEGMENT = '001' THEN 'PBB' ELSE 'CIB' END) AS market_segment, b.ADDRESS_LINE1 address_line1, "
//            + "b.ADDRESS_LINE2 address_line2, b.ADDRESS_LINE3 address_line3, c.gcifid global_cif, b.city city, "
//            + "b.zip post_code, b.COUNTRY_CODE COUNTRY_CODE, b.STATE province "
//            + "FROM tbaadm.gam a "
//            + "JOIN crmuser.address b ON a.cif_id = b.orgkey "
//            + "JOIN crmuser.corporate c ON a.cif_id = c.corp_key "
//            + "WHERE a.cif_id = :cifId AND b.PREFERREDADDRESS = 'Y'", nativeQuery = true)
//    FinacleDtdHtdTxnDataResponse getFinacleDtdHtdTxnDataResponse(@Param("cifId") String cifId);


    /*OLD Where I was searching  by account number. But new requiremenat want us to search by cid instead*/
//    @Query(value = "select a.foracid, a.cif_id cif, c.CORPORATE_NAME client_name,\n" +
//            "        c.SEGMENT  market_segment_code, (\n CASE                                          \n" +
//            "                                    WHEN c.SEGMENT = '001' THEN\n 'PBB' ELSE \n" +
//            "                                       'CIB'\n" +
//            "                                      END\n" +
//            "                                ) AS market_segment,\n" +
//            "                                b.ADDRESS_LINE1 address_line1,\n" +
//            "                                b.ADDRESS_LINE1 address_line2,\n" +
//            "                                b.ADDRESS_LINE1 address_line3,\n" +
//            "                                c.gcifid  global_cif,\n" +
//            "                                b.city city, \n" +
//            "                                b.zip post_code ,\n" +
//            "                                b.COUNTRY_CODE COUNTRY_CODE,\n" +
//            "                                b.STATE province\n" +
//            "from tbaadm.gam a , crmuser.address b , crmuser.corporate c where a.foracid =:accountNumber and a.cif_id = b.orgkey  and a.cif_id = c.corp_key and b.PREFERREDADDRESS = 'Y'", nativeQuery = true)
//    FinacleDtdHtdTxnDataResponse getFinacleDtdHtdTxnDataResponse(@Param("accountNumber")String accountNumber);


}
