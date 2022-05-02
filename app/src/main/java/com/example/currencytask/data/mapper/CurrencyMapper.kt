package com.example.currencytask.data.mapper

import com.example.currencytask.data.remote.dto.ChangeCurrencyResponseDto
import com.example.currencytask.data.remote.dto.RatesDto
import com.example.currencytask.domain.model.ChangeCurrencyResponse
import com.example.currencytask.domain.model.Rates

fun ChangeCurrencyResponseDto.toChangeCurrencyResponse():ChangeCurrencyResponse{
    return ChangeCurrencyResponse(
        base = base,
        date = date,
        ratesDto = ratesDto?.toRates(),
        success = success,
        timestamp = timestamp
    )
}

fun RatesDto.toRates():Rates{
    return Rates(aED,aFN, aLL, aMD, aNG, aOA, aRS, aUD, aWG, aZN, bAM, bBD, bDT, bGN, bHD, bIF, bMD, bND, bOB, bRL, bSD, bTC, bTN, bWP, bYN, bYR, bZD, cAD, cDF, cHF, cLF, cLP, cNY, cOP, cRC, cUC, cUP, cVE, cZK, dJF, dKK, dOP, dZD, eGP, eRN, eTB, eUR, fJD, fKP, gBP, gEL, gGP, gHS, gIP, gMD, gNF, gTQ, gYD, hKD, hNL, hRK, hTG, hUF, iDR, iLS, iMP, iNR, iQD, iRR, iSK, jEP, jMD, jOD, jPY, kES, kGS, kHR, kMF, kPW, kRW, kWD, kYD, kZT, lAK, lBP, lKR, lRD, lSL, lTL, lVL, lYD, mAD, mDL, mGA, mKD, mMK, mNT, mOP, mRO, mUR, mVR, mWK, mXN, mYR, mZN, nAD, nGN, nIO, nOK, nPR, nZD, oMR, pAB, pEN, pGK, pHP, pKR, pLN, pYG, qAR, rON, rSD, rUB, rWF, sAR, sBD, sCR, sDG, sEK, sGD, sHP, sLL, sOS, sRD, sTD, sVC, sYP, sZL, tHB, tJS, tMT, tND, tOP, tRY, tTD, tWD, tZS, uAH, uGX, uSD, uYU, uZS, vEF, vND, vUV, wST, xAF, xAG, xAU, xCD, xDR, xOF, xPF, yER, zAR, zMK, zMW, zWL)
}