<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class TestController extends AbstractController
{
     /**
     * @Route("/admin", name="display_admin")
     */
    public function indexAdmin(): Response
    {
        $this->denyAccessUnlessGranted('ROLE_ADMIN');

        return $this->render('Admin/index.html.twig'
        );
    }

    #[Route('/', name: 'app_clientt')]
    public function index(): Response
    {  
        $this->denyAccessUnlessGranted('ROLE_USER');
        $clients = $this->getDoctrine()->getRepository(Clientt::class)->findAll();
        return $this->render('clientt/index.html.twig', ['clients' => $clients]);
       
    }
}
