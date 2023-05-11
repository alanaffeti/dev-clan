<?php

namespace App\Controller;

use App\Entity\Accident;
use App\Repository\AccidentRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\SerializerInterface;

class Controllerfront extends AbstractController
{
    #[Route('/controllerfront', name: 'app_controllerfront')]
    public function index(): Response
    {
        return $this->render('front/index.html.twig', [
            'controller_name' => 'Controllerfront',
        ]);
    }




    #[Route('/front', name: 'client_front')]
    public function frontindex(): Response
    {
        return $this->render('base.html.twig');
    }
















}
