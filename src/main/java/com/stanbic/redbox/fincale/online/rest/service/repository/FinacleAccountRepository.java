package com.stanbic.redbox.fincale.online.rest.service.repository;

import com.stanbic.redbox.fincale.online.rest.service.entity.FinacleAccountResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinacleAccountRepository extends CrudRepository<FinacleAccountResponse, String> {
    /*Old using Account Numer*/
//    @Query(value = "select foracid ACCOUNT_NUMBER, (CASE WHEN ACCT_CLS_FLG = 'N' THEN 'ACTIVE' ELSE 'INACTIVE' END) ACCOUNT_STATUS, ACCT_CRNCY_CODE CURRENCY,SCHM_CODE ACCOUNT_TYPE from tbaadm.gam where foracid=:accountNumber", nativeQuery = true)
//    FinacleAccountResponse getFinacleAccountResponse(@Param("accountNumber")String accountNumber);
    //UAT
	@Query(value = "select CIF_ID, foracid ACCOUNT_NUMBER, (CASE WHEN ACCT_CLS_FLG = 'N' THEN 'ACTIVE' ELSE 'INACTIVE' END) ACCOUNT_STATUS, ACCT_CRNCY_CODE CURRENCY,(CASE WHEN SCHM_CODE = 'ODLC' THEN 'ILOC' WHEN SCHM_CODE = 'ODBDG' THEN 'LOGT' ELSE 'CASH' END) ACCOUNT_TYPE from tbaadm.gam@red_link  WHERE cif_id=:cif and SCHM_TYPE = 'ODA'", nativeQuery = true)
  //  @Query(value = "select  ACCOUNT_NUMBER, (CASE WHEN ACCT_CLS_FLG = 'N' THEN 'ACTIVE' ELSE 'INACTIVE' END) ACCOUNT_STATUS, CURRENCY, ACCOUNT_TYPE from RBQ_Finacle_Data_Response where cif_id=:cif", nativeQuery = true)

    List<FinacleAccountResponse> getFinacleAccountResponse(@Param("cif")String cif);


}
