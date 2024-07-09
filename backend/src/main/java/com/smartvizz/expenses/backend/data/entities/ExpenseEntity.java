package com.smartvizz.expenses.backend.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "expenses")
public class ExpenseEntity {
    public enum Currency {
        AED, // United Arab Emirates Dirham
        AFN, // Afghan Afghani
        ALL, // Albanian Lek
        AMD, // Armenian Dram
        ANG, // Netherlands Antillean Guilder
        AOA, // Angolan Kwanza
        ARS, // Argentine Peso
        AUD, // Australian Dollar
        AWG, // Aruban Florin
        AZN, // Azerbaijani Manat
        BAM, // Bosnia-Herzegovina Convertible Mark
        BBD, // Barbadian Dollar
        BDT, // Bangladeshi Taka
        BGN, // Bulgarian Lev
        BHD, // Bahraini Dinar
        BIF, // Burundian Franc
        BMD, // Bermudian Dollar
        BND, // Brunei Dollar
        BOB, // Bolivian Boliviano
        BRL, // Brazilian Real
        BSD, // Bahamian Dollar
        BTN, // Bhutanese Ngultrum
        BWP, // Botswana Pula
        BYN, // Belarusian Ruble
        BZD, // Belize Dollar
        CAD, // Canadian Dollar
        CDF, // Congolese Franc
        CHF, // Swiss Franc
        CLP, // Chilean Peso
        CNY, // Chinese Yuan
        COP, // Colombian Peso
        CRC, // Costa Rican Colón
        CUP, // Cuban Peso
        CVE, // Cape Verdean Escudo
        CZK, // Czech Koruna
        DJF, // Djiboutian Franc
        DKK, // Danish Krone
        DOP, // Dominican Peso
        DZD, // Algerian Dinar
        EGP, // Egyptian Pound
        ERN, // Eritrean Nakfa
        ETB, // Ethiopian Birr
        EUR, // Euro
        FJD, // Fijian Dollar
        FKP, // Falkland Islands Pound
        FOK, // Faroese Króna
        GBP, // British Pound Sterling
        GEL, // Georgian Lari
        GGP, // Guernsey Pound
        GHS, // Ghanaian Cedi
        GIP, // Gibraltar Pound
        GMD, // Gambian Dalasi
        GNF, // Guinean Franc
        GTQ, // Guatemalan Quetzal
        GYD, // Guyanese Dollar
        HKD, // Hong Kong Dollar
        HNL, // Honduran Lempira
        HRK, // Croatian Kuna
        HTG, // Haitian Gourde
        HUF, // Hungarian Forint
        IDR, // Indonesian Rupiah
        ILS, // Israeli New Shekel
        IMP, // Isle of Man Pound
        INR, // Indian Rupee
        IQD, // Iraqi Dinar
        IRR, // Iranian Rial
        ISK, // Icelandic Króna
        JEP, // Jersey Pound
        JMD, // Jamaican Dollar
        JOD, // Jordanian Dinar
        JPY, // Japanese Yen
        KES, // Kenyan Shilling
        KGS, // Kyrgyzstani Som
        KHR, // Cambodian Riel
        KID, // Kiribati Dollar
        KMF, // Comorian Franc
        KRW, // South Korean Won
        KWD, // Kuwaiti Dinar
        KYD, // Cayman Islands Dollar
        KZT, // Kazakhstani Tenge
        LAK, // Lao Kip
        LBP, // Lebanese Pound
        LKR, // Sri Lankan Rupee
        LRD, // Liberian Dollar
        LSL, // Lesotho Loti
        LYD, // Libyan Dinar
        MAD, // Moroccan Dirham
        MDL, // Moldovan Leu
        MGA, // Malagasy Ariary
        MKD, // Macedonian Denar
        MMK, // Myanmar Kyat
        MNT, // Mongolian Tögrög
        MOP, // Macanese Pataca
        MRU, // Mauritanian Ouguiya
        MUR, // Mauritian Rupee
        MVR, // Maldivian Rufiyaa
        MWK, // Malawian Kwacha
        MXN, // Mexican Peso
        MYR, // Malaysian Ringgit
        MZN, // Mozambican Metical
        NAD, // Namibian Dollar
        NGN, // Nigerian Naira
        NIO, // Nicaraguan Córdoba
        NOK, // Norwegian Krone
        NPR, // Nepalese Rupee
        NZD, // New Zealand Dollar
        OMR, // Omani Rial
        PAB, // Panamanian Balboa
        PEN, // Peruvian Sol
        PGK, // Papua New Guinean Kina
        PHP, // Philippine Peso
        PKR, // Pakistani Rupee
        PLN, // Polish Złoty
        PYG, // Paraguayan Guaraní
        QAR, // Qatari Riyal
        RON, // Romanian Leu
        RSD, // Serbian Dinar
        RUB, // Russian Ruble
        RWF, // Rwandan Franc
        SAR, // Saudi Riyal
        SBD, // Solomon Islands Dollar
        SCR, // Seychellois Rupee
        SDG, // Sudanese Pound
        SEK, // Swedish Krona
        SGD, // Singapore Dollar
        SHP, // Saint Helena Pound
        SLL, // Sierra Leonean Leone
        SOS, // Somali Shilling
        SRD, // Surinamese Dollar
        SSP, // South Sudanese Pound
        STN, // São Tomé and Príncipe Dobra
        SYP, // Syrian Pound
        SZL, // Eswatini Lilangeni
        THB, // Thai Baht
        TJS, // Tajikistani Somoni
        TMT, // Turkmenistani Manat
        TND, // Tunisian Dinar
        TOP, // Tongan Paʻanga
        TRY, // Turkish Lira
        TTD, // Trinidad and Tobago Dollar
        TVD, // Tuvaluan Dollar
        TWD, // New Taiwan Dollar
        TZS, // Tanzanian Shilling
        UAH, // Ukrainian Hryvnia
        UGX, // Ugandan Shilling
        USD, // United States Dollar
        UYU, // Uruguayan Peso
        UZS, // Uzbekistani Som
        VES, // Venezuelan Bolívar Soberano
        VND, // Vietnamese Đồng
        VUV, // Vanuatu Vatu
        WST, // Samoan Tālā
        XAF, // Central African CFA Franc
        XCD, // East Caribbean Dollar
        XOF, // West African CFA Franc
        XPF, // CFP Franc
        YER, // Yemeni Rial
        ZAR, // South African Rand
        ZMW, // Zambian Kwacha
        ZWL; // Zimbabwean Dollar
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = true, length = 1000)
    String description;

    @Column(nullable = false)
    Long amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 3, columnDefinition = "VARCHAR(3) DEFAULT 'PLN'")
    Currency currency;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Instant updatedAt;

    public ExpenseEntity(String title, String description, Long amount, Currency currency) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
    }

    public ExpenseEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
