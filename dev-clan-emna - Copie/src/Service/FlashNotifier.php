<?php

namespace App\Service;

use Symfony\Component\HttpFoundation\Session\Flash\FlashBagInterface;

class FlashNotifier
{
    private $flashBag;

    public function __construct(FlashBagInterface $flashBag)
    {
        $this->flashBag = $flashBag;
    }

    public function addSuccess($message)
    {
        $this->flashBag->add('success', $message);
    }

    public function addError($message)
    {
        $this->flashBag->add('error', $message);
    }
}