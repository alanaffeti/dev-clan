<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class ControllerfrontController extends AbstractController
{
    #[Route('/controllerfront', name: 'app_controllerfront')]
    public function index(): Response
    {
        return $this->render('controllerfront/index.html.twig', [
            'controller_name' => 'Controllerfront',
        ]);
    }
}
