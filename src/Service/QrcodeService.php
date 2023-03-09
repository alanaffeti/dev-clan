<?php

namespace App\Service;

use Endroid\QrCode\Builder\BuilderInterface;
use Endroid\QrCode\Encoding\Encoding;
use Endroid\QrCode\ErrorCorrectionLevel\ErrorCorrectionLevelHigh;

class QrcodeService
{
    private $builder;

    public function __construct(BuilderInterface $builder)
    {

        $this->builder = $builder;
    }

    public function qrcode($query)
    {
        $url = 'https://www.google.com/search?q=';

        $result = $this->builder
            ->size(400)
            ->encoding(new Encoding('UTF-8'))
            ->errorCorrectionLevel(new ErrorCorrectionLevelHigh())
            ->data(20)
            ->margin(10)
            ->labelText('qr code de assuransea')
            ->build();

        $namePng = uniqid('',''). '.png';
        $result->saveToFile( (\dirname(__DIR__,2).'/public/uploads/qr-code/'.$namePng));


           return $result->getDataUri();

    }
}