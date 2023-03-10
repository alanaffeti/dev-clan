<?php

namespace App\Controller;
use App\Entity\Reclamation;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\HttpFoundation\Request;
use App\Repository\ReclamationRepository;



class TestController extends AbstractController
{
    #[Route('/test', name: 'app_test')]
    public function index(): Response
    {
        return $this->render('test/index.html.twig', [
            'controller_name' => 'TestController',
        ]);
    }
    #[Route('/front', name: 'client_front')]
    public function frontindex(): Response
    {
    return $this->render('front/index.html.twig');
    }
    #[Route("addReclamationJSON/new", name: "addreclamationJSON")]
    public function addReclamationJSON(Request $req,NormalizerInterface $Normalizer)
    {

        $em = $this->getDoctrine()->getManager();
        $reclamation = new Reclamation();
        $reclamation->setNom($req->get('Nom'));
        $reclamation->setPrenom($req->get('Prenom'));
        $reclamation->setDescription($req->get('Description'));
        $reclamation->setTypeRec($req->get('type_rec'));
        $reclamation->setEmail($req->get('Email'));
        $em->persist($reclamation);
        $em->flush();

        $jsonContent = $Normalizer->normalize($reclamation, 'js on', ['groups' => 'reclamation']);
        return new Response(json_encode($jsonContent));
    }

}
